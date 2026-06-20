insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Roma', 1927, 'Roma');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Lazio', 1900, 'Roma');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Juventus', 1897, 'Torino');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Milan', 1899, 'Milano');
insert into team (id, name, year_of_foundation, city) values(nextval('team_seq'), 'Inter', 1908, 'Milano');

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Paolo', 'Rossi', '1990-09-23', 'ATTACCANTE', 182, (select id from team where name = 'Juventus'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Marco', 'Bianchi', '1995-02-12', 'CENTROCAMPISTA', 178, (select id from team where name = 'Roma'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Luca', 'Verdi', '1998-07-04', 'DIFENSORE', 185, (select id from team where name = 'Inter'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Andrea', 'Neri', '1992-11-15', 'PORTIERE', 191, (select id from team where name = 'Milan'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Davide', 'Conti', '1996-05-08', 'ATTACCANTE', 180, (select id from team where name = 'Lazio'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Giorgio', 'Serra', '1993-01-30', 'DIFENSORE', 187, (select id from team where name = 'Roma'));

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

insert into users (id, name, surname, username) values(nextval('users_seq'), 'Paolo', 'Verdi', 'paolo');

insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'paolo', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN', (select id from users where username = 'paolo'));
insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'admin', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN', (select id from users where username = 'admin'));

insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'mario', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'DEFAULT', (select id from users where username = 'mario'));
insert into credentials (id, username, password, role, user_id) values(nextval('credentials_seq'), 'luigi', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'DEFAULT', (select id from users where username = 'luigi'));

insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Roma'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Lazio'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Juventus'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Serie A' and year = 2025), (select id from team where name = 'Milan'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Inter'));
insert into tournament_teams (tournaments_id, teams_id) values((select id from tournament where name = 'Coppa Italia' and year = 2025), (select id from team where name = 'Roma'));

-- Aggiunta giocatori per avere almeno 4 giocatori per squadra
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Alessandro', 'Del Piero', '1974-11-09', 'ATTACCANTE', 174, (select id from team where name = 'Juventus'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Gianluigi', 'Buffon', '1978-01-28', 'PORTIERE', 192, (select id from team where name = 'Juventus'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Giorgio', 'Chiellini', '1984-08-14', 'DIFENSORE', 187, (select id from team where name = 'Juventus'));

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Francesco', 'Totti', '1976-09-27', 'ATTACCANTE', 180, (select id from team where name = 'Roma'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Daniele', 'De Rossi', '1983-07-24', 'CENTROCAMPISTA', 184, (select id from team where name = 'Roma'));

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Javier', 'Zanetti', '1973-08-10', 'DIFENSORE', 178, (select id from team where name = 'Inter'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Diego', 'Milito', '1979-06-12', 'ATTACCANTE', 183, (select id from team where name = 'Inter'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Wesley', 'Sneijder', '1984-06-09', 'CENTROCAMPISTA', 170, (select id from team where name = 'Inter'));

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Paolo', 'Maldini', '1968-06-26', 'DIFENSORE', 186, (select id from team where name = 'Milan'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Gennaro', 'Gattuso', '1978-01-09', 'CENTROCAMPISTA', 177, (select id from team where name = 'Milan'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Filippo', 'Inzaghi', '1973-08-09', 'ATTACCANTE', 181, (select id from team where name = 'Milan'));

insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Alessandro', 'Nesta', '1976-03-19', 'DIFENSORE', 187, (select id from team where name = 'Lazio'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Ciro', 'Immobile', '1990-02-20', 'ATTACCANTE', 185, (select id from team where name = 'Lazio'));
insert into player (id, name, surname, date_of_birth, role, height, team_id) values(nextval('player_seq'), 'Sergej', 'Milinkovic-Savic', '1995-02-27', 'CENTROCAMPISTA', 191, (select id from team where name = 'Lazio'));

-- Aggiunta commenti
insert into comments (id, text, author_id, match_id) values(nextval('comment_seq'), 'Partita incredibile, il derby non delude mai!', (select id from users where username = 'mario'), (select id from match where date = '2025-09-01'));
insert into comments (id, text, author_id, match_id) values(nextval('comment_seq'), 'La Lazio meritava di più secondo me.', (select id from users where username = 'luigi'), (select id from match where date = '2025-09-01'));

insert into comments (id, text, author_id, match_id) values(nextval('comment_seq'), 'Un classico del calcio italiano, sempre bello vederlo.', (select id from users where username = 'paolo'), (select id from match where date = '2025-09-07'));

insert into comments (id, text, author_id, match_id) values(nextval('comment_seq'), 'Non vedo l''ora di guardarla allo stadio!', (select id from users where username = 'mario'), (select id from match where date = '2025-11-20'));