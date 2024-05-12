CREATE TABLE file_sending_task (
id serial PRIMARY KEY,
file_name VARCHAR(50),
status VARCHAR(20),
required_columns VARCHAR(500)
);