import { CausaFactor } from "./causaFactor";
import { ControlFactor } from "./controlFactor";
import { EfectoFactor } from "./efectoFactor";
import { Ejercicios } from "./ejercicios";


export class FactorRiesgo {

    factorRiesgoId: number;
    ejercicios: Ejercicios;
    procesoId: number;
    factorRiesgo: string;
    descripcionFactor: string;
    responsable: string;
    tipoRiesgoId: number;
    respuestaId: number;
    controlId: number;
    probabilidad: number;
    historico: number;
    impacto: number;
    severidad: number;
    riesgoResidual: number;
    estatus: number;
    alerta: string;
    causaRiesgo: CausaFactor[];
    efectoRiesgo: EfectoFactor[];
    controlesRiesgo: ControlFactor[];

}