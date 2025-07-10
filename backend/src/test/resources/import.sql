-- =============================================
-- INSERTS PARA A TABELA: PERSON
-- =============================================
-- Assumindo que os IDs serão gerados como 1, 2, 3, 4, 5, 6
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (1, 'Ana Silva', 'ana.silva@example.com', '1990-05-15', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (2, 'Bruno Costa', 'bruno.costa@example.com', '1985-11-20', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (3, 'Carla Dias', 'carla.dias@example.com', '1992-02-10', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (4, 'Daniel Farias', 'daniel.farias@example.com', '1988-07-01', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (5, 'Eduarda Lima', 'eduarda.lima@example.com', '1995-09-25', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (6, 'Fábio Martins', 'fabio.martins@example.com', '1982-03-30', '0001-01-01', '12345678', 1);
INSERT INTO PERSON (id, name, email, birthdate, exclusionDate, password, role) VALUES (7, 'Gabriela Rocha', 'gabriela.rocha@example.com', '1999-12-05', '0001-01-01', '12345678', 1);


-- =============================================
-- INSERTS PARA A TABELA: DEPARTMENT
-- =============================================
-- Assumindo que os IDs serão gerados como 1, 2, 3
INSERT INTO DEPARTMENT (id, name, description) VALUES (1, 'Eletrônicos', 'Departamento de produtos eletrônicos e gadgets.');
INSERT INTO DEPARTMENT (id, name, description) VALUES (2, 'Livros', 'Departamento de livros, revistas e material de leitura.');
INSERT INTO DEPARTMENT (id, name, description) VALUES (3, 'Vestuário', 'Departamento de roupas e acessórios de moda.');
INSERT INTO DEPARTMENT (id, name, description) VALUES (4, 'Alimentos', 'Departamento de produtos alimentícios e bebidas.');


-- =============================================
-- INSERTS PARA A TABELA: CLIENT
-- =============================================
-- Usando person_id's das inserções em PERSON
-- Assumindo que os IDs de CLIENT serão gerados como 1, 2, 3
INSERT INTO CLIENT (id, person_id, registrationDate, notifyPromotion) VALUES (1, 1, '2023-01-10', true);  -- Ana Silva
INSERT INTO CLIENT (id, person_id, registrationDate, notifyPromotion) VALUES (2, 2, '2023-02-15', false); -- Bruno Costa
INSERT INTO CLIENT (id, person_id, registrationDate, notifyPromotion) VALUES (3, 3, '2023-03-20', true);  -- Carla Dias
INSERT INTO CLIENT (id, person_id, registrationDate, notifyPromotion) VALUES (4, 7, '2023-04-25', false); -- Gabriela Rocha


-- =============================================
-- INSERTS PARA A TABELA: SELLER
-- =============================================
-- Usando person_id's das inserções em PERSON (diferentes dos clientes)
-- Assumindo que os IDs de SELLER serão gerados como 1, 2, 3
INSERT INTO SELLER (id, person_id, baseSalary, numberHours) VALUES (1, 4, 3000.00, 40.0); -- Daniel Farias
INSERT INTO SELLER (id, person_id, baseSalary, numberHours) VALUES (2, 5, 3200.00, 44.0); -- Eduarda Lima
INSERT INTO SELLER (id, person_id, baseSalary, numberHours) VALUES (3, 6, 2800.00, 38.5); -- Fábio Martins


-- =============================================
-- INSERTS PARA A TABELA: PRODUCT
-- =============================================
-- Usando department_id's das inserções em DEPARTMENT
-- Assumindo que os IDs de PRODUCT serão gerados como 1, 2, 3, 4, 5
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (1, 1, 'Smartphone XPTO', 'Smartphone avançado com câmera de 108MP', 2999.90);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (2, 1, 'Notebook Pro', 'Notebook de alta performance para profissionais', 7500.00);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (3, 2, 'O Senhor dos Anéis', 'Edição de colecionador da trilogia completa', 150.75);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (4, 3, 'Camiseta Algodão Pima', 'Camiseta básica de algodão Pima ultra macio', 89.90);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (5, 1, 'Fone de Ouvido Bluetooth', 'Fone sem fio com cancelamento de ruído', 499.50);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (6, 2, 'A Revolução dos Bichos', 'Clássico de George Orwell', 35.00);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (7, 3, 'Calça Jeans Slim', 'Calça jeans masculina modelo slim fit', 199.00);
INSERT INTO PRODUCT (id, department_id, name, description, price) VALUES (8, 4, 'Café Especial Arábica', 'Pacote de 500g de café especial moído', 45.00);


-- =============================================
-- INSERTS PARA A TABELA: SALE
-- =============================================
-- Usando client_id's e seller_id's das inserções anteriores
-- Assumindo que os IDs de SALE serão gerados como 1, 2, 3
INSERT INTO SALE (id, client_id, seller_id, dateSale, price) VALUES (1, 1, 1, '2024-05-01', 3499.40); -- Cliente Ana, Vendedor Daniel
INSERT INTO SALE (id, client_id, seller_id, dateSale, price) VALUES (2, 2, 2, '2024-05-03', 150.75);  -- Cliente Bruno, Vendedora Eduarda
INSERT INTO SALE (id, client_id, seller_id, dateSale, price) VALUES (3, 1, 1, '2024-05-10', 89.90);   -- Cliente Ana, Vendedor Daniel (outra compra)
INSERT INTO SALE (id, client_id, seller_id, dateSale, price) VALUES (4, 3, 3, '2024-05-12', 7699.00); -- Cliente Carla, Vendedor Fábio
INSERT INTO SALE (id, client_id, seller_id, dateSale, price) VALUES (5, 4, 2, '2024-05-15', 45.00);   -- Cliente Gabriela, Vendedora Eduarda


-- =============================================
-- INSERTS PARA A TABELA: SALE_PRODUCT (Tabela de Junção)
-- =============================================
-- Ligando SALE_ID com PRODUCT_ID
-- Venda 1 (Cliente Ana, Vendedor Daniel) comprou Smartphone XPTO (1) e Fone Bluetooth (5)
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (1, 1); -- Smartphone XPTO na venda 1
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (1, 5); -- Fone Bluetooth na venda 1

-- Venda 2 (Cliente Bruno, Vendedora Eduarda) comprou O Senhor dos Anéis (3)
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (2, 3); -- O Senhor dos Anéis na venda 2

-- Venda 3 (Cliente Ana, Vendedor Daniel) comprou Camiseta Algodão (4)
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (3, 4); -- Camiseta Algodão Pima na venda 3

-- Venda 4 (Cliente Carla, Vendedor Fábio) comprou Notebook Pro (2) e Calça Jeans Slim (7)
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (4, 2); -- Notebook Pro na venda 4
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (4, 7); -- Calça Jeans Slim na venda 4

-- Venda 5 (Cliente Gabriela, Vendedora Eduarda) comprou Café Especial Arábica (8)
INSERT INTO SALE_PRODUCT (sale_id, product_id) VALUES (5, 8); -- Café Especial Arábica na venda 5


ALTER TABLE PERSON ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM PERSON) + 1;
ALTER TABLE DEPARTMENT ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM DEPARTMENT) + 1;
ALTER TABLE CLIENT ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM CLIENT) + 1;
ALTER TABLE SELLER ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM SELLER) + 1;
ALTER TABLE SALE ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM SALE) + 1;
ALTER TABLE PRODUCT ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM PRODUCT) + 1;
