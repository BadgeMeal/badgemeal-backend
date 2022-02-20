use badgeMeal;
INSERT INTO badgeMeal.bm_user (address) VALUES ('0x414c8685bcfbef5524b5a6aa859155d849529bab');
INSERT INTO badgeMeal.bm_user (address) VALUES ('0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162');

INSERT INTO badgeMeal.bm_round (round, date, is_now) VALUES (0, '2022-02-17', null);
INSERT INTO badgeMeal.bm_round (round, date, is_now) VALUES (1, '2022-02-18', 'Y');

INSERT INTO badgeMeal.bm_draw (draw_id, count, draw_info_round, user_address) VALUES (0, 0, 0, '0x414c8685bcfbef5524b5a6aa859155d849529bab');
INSERT INTO badgeMeal.bm_draw (draw_id, count, draw_info_round, user_address) VALUES (1, 0, 0, '0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162');
INSERT INTO badgeMeal.bm_draw (draw_id, count, draw_info_round, user_address) VALUES (2, 1, 1, '0x414c8685bcfbef5524b5a6aa859155d849529bab');
INSERT INTO badgeMeal.bm_draw (draw_id, count, draw_info_round, user_address) VALUES (3, 1, 1, '0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162');

INSERT INTO badgeMeal.bm_menu (menu_no, type, keyword) VALUES (0, '김치찌개', '김치찌개');