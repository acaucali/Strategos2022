ALTER TABLE iniciativa 
ADD anio_form_proy  varchar(4) Null;

alter table explicacion_adjunto drop column documento;

alter table explicacion_adjunto drop column mime_type;

alter table explicacion_adjunto add ruta varchar(100);


UPDATE afw_sistema set actual = '8.01-180918';


