-- Seed users. Passwords are BCrypt encoded.
-- All users have password: admin123

INSERT INTO users (username, password_hash, email, full_name, role) VALUES
('admin',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'admin@banking.com',      'System Admin',    'ADMIN'),
('user1',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'john.doe@banking.com',   'John Doe',        'USER'),
('user2',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'jane.smith@banking.com', 'Jane Smith',      'USER'),
('user3',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'bob.wilson@banking.com', 'Robert Wilson',   'USER'),
('user4',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'emily.chen@banking.com', 'Emily Chen',      'USER'),
('user5',   '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'mike.b@banking.com',     'Michael Brown',   'USER'),
('analyst', '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'analyst@banking.com',    'Data Analyst',    'USER'),
('manager', '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'manager@banking.com',    'Branch Manager',  'ADMIN'),
('support', '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'support@banking.com',    'Support Agent',   'USER'),
('auditor', '$2a$10$tIc6sDRjj0GHQqKoN49oDeB/qRdME//6ssQxOMGz8MU7ePgEO49BG', 'auditor@banking.com',    'Internal Auditor','USER');

