import { EstatusIdeas } from "./estatusideas";
import { IdeasDocumentosAnexos } from "./ideasdocumentosanexos";
import { TiposObjetivos } from "./tiposobjetivos";
import { TiposPropuestas } from "./tipospropuestas";

export class IdeasProyectos {

    ideaId: number;
    nombreIdea: String;
    descripcionIdea: String;
    tipoPropuestaId: number;
    impacto: String;
    problematica: String;
    poblacion: String;
    focalizacion: String;
    tipoObjetivoId: number;
    financiacion: String;
    dependenciasParticipantes: String;
    dependenciaPersona: String;
    personaEncargada: String;
    personaContactoDatos: String;
    dependenciaId: number;
    proyectosEjecutados: String;
    capacidadTecnica: String;
    fechaIdea: Date;
    anioFormulacion: String;
    estatusIdeaId: EstatusIdeas;
    fechaEstatus: Date;
    historico: number;
    valorUltimaEvaluacion: number;
    fechaUltimaEvaluacion: Date;
    observaciones: String;
    documentoId: number;

}