CREATE TABLE esfoFonte
CREATE TABLE esfoFonte
(
   cdFonte decimal(10,0) PRIMARY KEY NOT NULL,
   deFonte varchar(100) NOT NULL,
   sgFonte varchar(20),
   cdTpFonte decimal(2,0),   
   flForaUso char(1),
   cdFontePai decimal(10,0)
);

CREATE TABLE esfoTpFonte
(
    cdTpFonte decimal(2,0) PRIMARY KEY NOT NULL,
    deTpFonte varchar(100) NOT NULL,
    flForaUso char(1)	
);

CREATE TABLE ecdtMunicipio 
( 
    cdMunicipio decimal(5,0) PRIMARY KEY NOT NULL,
    sgUnidadeFederal varchar(2) NOT NULL,
    nmMunicipio varchar(50) NOT NULL,
    flForaUso char(1),	
    cdMunicipioIBGE decimal(7,0),
    qtpopulacao decimal(8,0),
    flRegiaoMetropol char(1)
);

CREATE TABLE esfoPrefeitura
(
    cdMunicipio decimal(5,0) PRIMARY KEY NOT NULL,
    nuCnpj varchar(18)    
);

