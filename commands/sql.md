-- =============================================================================
-- FUNÇÕES PARA GERENCIAMENTO DE RESERVAS DE EQUIPAMENTOS
-- =============================================================================
-- Este script cria funções PL/pgSQL para encapsular a lógica de negócio
-- diretamente no banco de dados, garantindo consistência e simplificando
-- as chamadas da aplicação.
-- =============================================================================


-- =============================================================================
-- FUNÇÃO 1: Realizar uma nova reserva
-- =============================================================================
-- Esta função é transacional. Ela cria um novo registro de reserva e
-- atualiza o status do equipamento para 'reservado'. Se qualquer passo
-- falhar, toda a operação é desfeita (rollback).
--
-- Parâmetros:
--   p_equipamento_id: O UUID do equipamento a ser reservado.
--   p_pessoa_matricula: A matrícula da pessoa que está reservando.
--   p_devolucao_prevista: A data em que se espera a devolução.
--
-- Retorna:
--   O UUID da nova reserva criada.
--
CREATE OR REPLACE FUNCTION reservar_equipamento(
p_equipamento_id UUID,
p_pessoa_matricula VARCHAR(50),
p_devolucao_prevista DATE
)
RETURNS UUID AS $$
DECLARE
v_equipamento_status status_equipamento;
v_nova_reserva_id UUID;
BEGIN
-- Garante que apenas uma operação por vez modifique a linha do equipamento
-- para evitar condições de corrida (duas pessoas reservando ao mesmo tempo).
SELECT status INTO v_equipamento_status
FROM equipamentos
WHERE id = p_equipamento_id
FOR UPDATE;

    -- Verifica se o equipamento está realmente disponível
    IF v_equipamento_status != 'disponivel' THEN
        RAISE EXCEPTION 'Equipamento com ID % não está disponível para reserva. Status atual: %', p_equipamento_id, v_equipamento_status;
    END IF;

    -- Atualiza o status do equipamento para 'reservado'
    UPDATE equipamentos
    SET status = 'reservado'
    WHERE id = p_equipamento_id;

    -- Insere o novo registro na tabela de reservas
    INSERT INTO reservas (equipamento_id, pessoa_matricula, data_devolucao_prevista)
    VALUES (p_equipamento_id, p_pessoa_matricula, p_devolucao_prevista)
    RETURNING id INTO v_nova_reserva_id;

    -- Retorna o ID da reserva recém-criada
    RETURN v_nova_reserva_id;
END;
$$ LANGUAGE plpgsql;


-- =============================================================================
-- FUNÇÃO 2: Realizar a devolução de um equipamento
-- =============================================================================
-- Esta função finaliza uma reserva ativa, registrando a data de devolução
-- e atualizando o status do equipamento de volta para 'disponivel'.
--
-- Parâmetros:
--   p_reserva_id: O UUID da reserva que está sendo finalizada.
--
-- Retorna:
--   TRUE se a operação foi bem-sucedida.
--
CREATE OR REPLACE FUNCTION devolver_equipamento(
p_reserva_id UUID
)
RETURNS BOOLEAN AS $$
DECLARE
v_equipamento_id UUID;
BEGIN
-- Encontra o ID do equipamento associado à reserva e trava a linha
SELECT equipamento_id INTO v_equipamento_id
FROM reservas
WHERE id = p_reserva_id AND data_devolucao_final IS NULL
FOR UPDATE;

    -- Se não encontrou uma reserva ativa com esse ID, lança um erro
    IF v_equipamento_id IS NULL THEN
        RAISE EXCEPTION 'Nenhuma reserva ativa encontrada com o ID %', p_reserva_id;
    END IF;

    -- Atualiza a reserva, preenchendo a data de devolução final
    UPDATE reservas
    SET data_devolucao_final = CURRENT_DATE
    WHERE id = p_reserva_id;

    -- Atualiza o status do equipamento para 'disponivel'
    UPDATE equipamentos
    SET status = 'disponivel'
    WHERE id = v_equipamento_id;

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


-- =============================================================================
-- VIEW 3: Listar equipamentos que estão atualmente disponíveis
-- =============================================================================
-- Uma VIEW é como uma tabela virtual que simplifica consultas repetitivas.
-- Usar esta view é mais fácil do que escrever a query completa toda vez.
--
CREATE OR REPLACE VIEW vw_equipamentos_disponiveis AS
SELECT
eq.id,
eq.nome,
eq.descricao,
eq.patrimonio,
tp.nome AS tipo_equipamento
FROM
equipamentos eq
JOIN
tipos_equipamento tp ON eq.tipo_id = tp.id
WHERE
eq.status = 'disponivel';

-- Para usar a view, basta fazer:
-- SELECT * FROM vw_equipamentos_disponiveis;

