/* 
 * Copyright (C) 2022 paulo.rodrigues
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Author:  paulo.rodrigues
 * Created: Jun 23, 2022
 */
insert into role (id, name) values
(1, 'ADMIN'),
(3, 'OPERATOR'),
(2, 'CLIENT');

insert into lbs_user (id, name, username, password) values 
(1, 'Paulo Rodrigues', 'paulo', '{noop}1');

insert into user_role (user_id, role_id) values
(1,1);
