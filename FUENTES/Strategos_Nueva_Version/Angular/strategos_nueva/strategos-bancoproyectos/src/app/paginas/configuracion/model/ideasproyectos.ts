import { EstatusIdeas } from "./estatusideas";
import { EvaluacionIdeas } from "./evaluacionideas";


export class IdeasProyectos {

    ideaId: number;
    nombreIdea: String;
    descripcionIdea: String;
    tipoPropuestaId: number;
    propuesta: String;
    impacto: String;
    problematica: String;
    poblacion: String;
    focalizacion: String;
    tipoObjetivoId: number;
    financiacion: String;
    dependenciasParticipantes: String;
    personaEncargada: String;
    contactoEmail: String;
    contactoTelefono: String;
    dependenciaId: number;
    organizacion: String;
    proyectosEjecutados: String;
    capacidadTecnica: String;
    anioFormulacion: String;
    estatusIdeaId: number;
    estatus: String;
    fechaEstatus: Date;
    fechaRadicacion: Date;
    historico: number;
    valorUltimaEvaluacion: number;
    fechaUltimaEvaluacion: Date;
    observaciones: String;
    objetivoGeneral: String;
    duracionTotal: String;
    documentoId: number;
    evaluacion: EvaluacionIdeas;

}