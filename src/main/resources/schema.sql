CREATE TABLE categorias_equipamento (
                                        id SERIAL PRIMARY KEY,
                                        nome VARCHAR(255) NOT NULL
);

CREATE TABLE equipamentos (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              nome VARCHAR(255) NOT NULL,
                              descricao TEXT,
                              tombamento VARCHAR(255),
                              categoria_id BIGINT NOT NULL,
                              img_url VARCHAR(255) NOT NULL,
                              status_equipamento VARCHAR(255) NOT NULL,
                              FOREIGN KEY (categoria_id) REFERENCES categorias_equipamento(id)
);
