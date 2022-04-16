import { EstatusIdeas } from "./estatusideas";
import { IdeasDocumentosAnexos } from "./ideasdocumentosanexos";
import { TiposObjetivos } from "./tiposobjetivos";
import { TiposPropuestas } from "./tipospropuestas";

export class IdeasProyectos {

    ideaId: number;
    nombreIdea: String;
    descripcionIdea: String;
    tiposPropuestas: TiposPropuestas;
    impacto: String;
    problematica: String;
    poblacion: String;
    focalizacion: String;
    tipoObjetivo: TiposObjetivos;
    alineacionPlan: number;
    financiacion: String;
    personaEncargada: String;
    personaContactoDatos: String;
    dependenciaId: number;
    proyectosEjecutados: String;
    capacidadTecnica: String;
    fechaIdea: Date;
    anioFormulacion: String;
    estatus: EstatusIdeas;
    estatusIdea: number;
    fechaEstatus: Date;
    historico: number;
    valorUltimaEvaluacion: number;
    fechaUltimaEvaluacion: Date;
    observaciones: String;
    documentos: IdeasDocumentosAnexos[];

}