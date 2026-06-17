insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Roma', 1927, 'Roma');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Lazio', 1900, 'Roma');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Juventus', 1897, 'Torino');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Milan', 1899, 'Milano');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Inter', 1908, 'Milano');

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Paolo', 'Rossi', '1990-09-23', 'Forward', 182, (select id from team where name = 'Juventus'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Marco', 'Bianchi', '1995-02-12', 'Midfielder', 178, (select id from team where name = 'Roma'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Luca', 'Verdi', '1998-07-04', 'Defender', 185, (select id from team where name = 'Inter'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Andrea', 'Neri', '1992-11-15', 'Goalkeeper', 191, (select id from team where name = 'Milan'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Davide', 'Conti', '1996-05-08', 'Forward', 180, (select id from team where name = 'Lazio'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Giorgio', 'Serra', '1993-01-30', 'Defender', 187, (select id from team where name = 'Roma'));

insert into referee (id, name, surname, referee_code) values(nextval('referee_seq'), 'Daniele', 'Greco', 'R-1001');
insert into referee (id, name, surname, referee_code) values(nextval('referee_seq'), 'Simone', 'Ferri', 'R-1002');

insert into tournament (id, name, year, description) values(nextval('tournament_seq'), 'Serie A', 2025, 'Campionato nazionale di massima serie.');
insert into tournament (id, name, year, description) values(nextval('tournament_seq'), 'Coppa Italia', 2025, 'Competizione a eliminazione diretta.');

insert into match (id, date, time, state, goals_home, goals_away, tournament_id, team_home_id, team_away_id, referee_id) values(nextval('match_seq'), '2025-09-01', '20:45:00', 'PLAYED', 3, 1, (select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Roma'), (select id from team where name = 'Lazio'), (select id from referee where referee_code = 'R-1001'));
insert into match (id, date, time, state, goals_home, goals_away, tournament_id, team_home_id, team_away_id, referee_id) values(nextval('match_seq'), '2025-09-07', '18:00:00', 'PLAYED', 2, 2, (select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Juventus'), (select id from team where name = 'Milan'), (select id from referee where referee_code = 'R-1002'));
insert into match (id, date, time, state, goals_home, goals_away, tournament_id, team_home_id, team_away_id, referee_id) values(nextval('match_seq'), '2025-10-15', '21:00:00', 'PLAYED', 2, 1, (select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Inter'), (select id from team where name = 'Roma'), (select id from referee where referee_code = 'R-1001'));

-- Partite SCHEDULED da effettuare
insert into match (id, date, time, state, tournament_id, team_home_id, team_away_id, referee_id) values(nextval('match_seq'), '2025-11-20', '20:45:00', 'SCHEDULED', (select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Milan'), (select id from team where name = 'Roma'), (select id from referee where referee_code = 'R-1001'));
insert into match (id, date, time, state, tournament_id, team_home_id, team_away_id, referee_id) values(nextval('match_seq'), '2025-12-10', '18:00:00', 'SCHEDULED', (select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Juventus'), (select id from team where name = 'Inter'), (select id from referee where referee_code = 'R-1002'));

insert into users (id, name, surname, username) values(nextval('users_seq'), 'Admin', 'Calcio', 'admin');
insert into users (id, name, surname, username) values(nextval('users_seq'), 'Mario', 'Rossi', 'mario');
insert into users (id, name, surname, username) values(nextval('users_seq'), 'Luigi', 'Bianchi', 'luigi');

insert into credentials (id, username, password, role) values(nextval('credentials_seq'), 'paolo', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN');
insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'admin', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN', (select id from users where username = 'admin'));

insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'mario', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'DEFAULT', (select id from users where username = 'mario'));
insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'luigi', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'DEFAULT', (select id from users where username = 'luigi'));

insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Roma'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Lazio'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Juventus'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Milan'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Inter'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Roma'));