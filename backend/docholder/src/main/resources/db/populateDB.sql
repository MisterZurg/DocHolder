INSERT INTO users (name, surname, email, phone, companyID) VALUES
--INSERT INTO users VALUES
('Vassily', 'Petrov', 'vpetrov@jr.com', '+7 (191) 322-22-33', 228),
('Pjotr', 'Vasechkin', 'pvasechkin@jr.com', '+7 (191) 223-33-22', 1337);
INSERT INTO users
SELECT gen_random_uuid()
WHERE NOT exists ( SELECT uid FROM users );