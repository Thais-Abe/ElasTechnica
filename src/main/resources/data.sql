use db_elastechnica;

insert into tipo (nome) values('Cliente'),('Técnico');

insert into setor (nome) values ('Administrativo'), ('Financeiro'), ('Recursos Humanos'), ('Comercial'), ('Operacional'), ('Tecnologia da Informação');

insert into status (nome) values ('Aguardando técnico'), ('Em atendimento'), ('Escalado para outro setor'), ('Finalizado');