-- Inserciones: users
INSERT INTO users (name, surname, username, email, password) VALUES
('Alice', 'Smith', 'alice123', 'alice@example.com', 'password1'),
('Bob', 'Johnson', 'bob234', 'bob@example.com', 'password2'),
('Charlie', 'Brown', 'charlie345', 'charlie@example.com', 'password3');

-- Inserciones: groups
INSERT INTO groups (name, user_id) VALUES
('Work', 1),
('Personal', 1),
('Hobbies', 2),
('School', 3);

-- Inserciones: tasks
INSERT INTO tasks (description, checked, group_id) VALUES
('Complete project report', FALSE, 1),
('Buy groceries', TRUE, 2),
('Learn guitar', FALSE, 3),
('Finish homework', TRUE, 1);
