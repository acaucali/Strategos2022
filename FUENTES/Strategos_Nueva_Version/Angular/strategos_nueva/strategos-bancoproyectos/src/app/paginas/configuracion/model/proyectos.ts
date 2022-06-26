import { logging } from "protractor";
import { TipoPoblacion } from "./tipopoblacion";

export class Proyectos {
    
    proyectoId: number;
    dependenciaId: number;
    organizacion: String;
    profesionalResponsable: String;
    contactoEmail: String;
    contactoTelefono: String;
    fechaRadicacion: Date;
    tipoProyectoId: number;
    tipo: String;
    codigoBdp: String;
    nombreProyecto: String;
    duracion: number;
    costoEstimado: String;
    dependenciaLider: number;
    tipoObjetivoId: number;
    pertinencia: String;
    problematica: String;
    dependenciasEstrategicas: String;
    sociosEstrategicos: String;
    cobertura: String;
    rolesParticipantes: String;
    antecedentes: String;
    justificacion: String;
    alcance: String;
    objetivoGeneral: String;
    financiacion: String;
    ideaId: number;
    fechaFormulacion: Date;
    anioFormulacion: String;
    estatusId: number;
    estatus: String;
    fechaEstatus: Date;
    historico: Boolean;
    metodologiaId: number;   
    poblaciones:  TipoPoblacion[];                                                                
}