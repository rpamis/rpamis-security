INSERT INTO test_version (name, id_card, phone, version)
VALUES
    -- 原始明文数据
    ('张三', '500101111118181952', '12345678965', 0),
    -- 新版本加密数据(带 ENC_SM4_ 前缀，标识加密字段)
    ('ENC_SM4_4e123e9554b4a5b30b53b5b27c4deb59',
     'ENC_SM4_afea507cb1a88c25807d29b905b49bec5222893c3ff712bf9841e10d0ec1ac12',
     'ENC_SM4_93250b1e19518cc1b36af85c54066051', 0),
    -- 旧版本加密数据(仅密文，无前缀)
    ('4e123e9554b4a5b30b53b5b27c4deb59', 'afea507cb1a88c25807d29b905b49bec5222893c3ff712bf9841e10d0ec1ac12',
     '93250b1e19518cc1b36af85c54066051', 0);

INSERT INTO test_nest (id, user_account)
VALUES
    (1, 'ENC_SM4_93250b1e19518cc1b36af85c54066051');
