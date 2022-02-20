use badgeMeal;
INSERT INTO badgeMeal.bm_user (address) VALUES ('0x414c8685bcfbef5524b5a6aa859155d849529bab');
INSERT INTO badgeMeal.bm_user (address) VALUES ('0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162');

INSERT INTO badgeMeal.bm_round (round, date, is_now) VALUES (0, '2022-02-17', null);
INSERT INTO badgeMeal.bm_round (round, date, is_now) VALUES (1, '2022-02-18', 'Y');

INSERT INTO badgeMeal.bm_draw (address, round, count) VALUES ('0x414c8685bcfbef5524b5a6aa859155d849529bab', 0, 0);
INSERT INTO badgeMeal.bm_draw (address, round, count) VALUES ('0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162', 0, 0);
INSERT INTO badgeMeal.bm_draw (address, round, count) VALUES ('0x414c8685bcfbef5524b5a6aa859155d849529bab', 1, 0);
INSERT INTO badgeMeal.bm_draw (address, round, count) VALUES ('0x9a46A47Ad641a47e0eB3500B28D80aE133FE0162', 1, 0);
INSERT INTO badgeMeal.bm_menu (menu_no, type, keyword) VALUES (0, '김치찌개', '김치찌개');

INSERT
    INTO badgeMeal.bm_draw_result (address, round, is_verified, menu_menu_no, mint_data_mint_data_id)
    VALUES ('0x414c8685bcfbef5524b5a6aa859155d849529bab', 1, 'N', 0, null);
