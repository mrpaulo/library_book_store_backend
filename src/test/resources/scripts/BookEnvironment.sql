INSERT INTO esfoFonte (cdFonte, deFonte, sgFonte, cdTpFonte, flForaUso, cdFontePai)
VALUES
(1,'TESOURO', 'TESOURO - 001', 1,'S', NULL),
(2,'RECURSOS VINCULADOS - FUNDO ESPECIAL DE DESPESA', 'FED - 002', 2,'S', NULL),
(3,'FUNDO ESPECIAL DE DESPESA', 'FED - 003', 3,'S', NULL),
(4,'TESOURO - VINCULADOS','TESOURO - 002',4,'S', NULL),
(1000000,'TESOURO-DOT.INICIAL E CRED.SUPLEMENTAR', NULL, 1, 'N', NULL),
(1001001,'RECURSOS DO TESOURO DO ESTADO','Tesouro', 1,'N', NULL),
(1003003,'RECURSOS PROVENIENTES DE ALIENACAO DE BENS', NULL, NULL, 'N', NULL),
(2000000,'RECURSOS VINCULADOS', NULL, NULL, 'N', NULL),
(2002001,'RECURSOS VINCULADOS ESTADUAIS','Tesouro', 2,'N', NULL),
(2002156,'RECURSOS VINCULADOS FUNDO ESPECIAL DE DESPESA', 'FED-002', 2,'N', NULL);

INSERT INTO esfoTpFonte (cdTpFonte, deTpFonte, flForaUso)
VALUES
(1, 'Tesouro', 'N'),
(2, 'Transferências e convênios estaduais-Vinculados', 'N'),
(3, 'Recursos Próprios de Fundos Especiais de Despesa-Vinculados', 'N'),
(4, 'Recursos Próprios da Administração Indireta', 'N'),
(5, 'Transferências e Convênios Federais-Vinculados', 'N'),
(6, 'Outras Fontes de Recursos', 'N'),
(7, 'Operações de Crédito', 'N'),
(8, 'Emendas Parlamentares Individuais', 'N'),
(9, 'Emendas Parlamentares Individuais', 'S')
