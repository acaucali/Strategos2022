--Configuracion
UPDATE Reporte SET configuracion = replace(configuracion, ',', '%-%');
UPDATE Reporte SET configuracion = replace(configuracion, 'atributos="1%-%1%-%Nombre%-%1%-%150%-%1|2%-%2%-%Organización%-%0%-%80%-%0|3%-%3%-%Clase%-%0%-%80%-%0|4%-%4%-%Unidad de Medida%-%1%-%80%-%1|5%-%5%-%Frecuencia%-%0%-%80%-%0|6%-%6%-%Serie%-%1%-%80%-%1"', 'atributos="1,1,Nombre,1,80,0|2,2,Organización,1,80,0|3,3,Clase,1,80,0|4,4,Unidad de Medida,1,80,0|5,5,Frecuencia,1,80,0|6,6,Serie,1,80,0"');
