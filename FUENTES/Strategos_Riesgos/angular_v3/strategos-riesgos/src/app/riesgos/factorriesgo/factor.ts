import { Ejercicios } from '../ejercicios/ejercicios';
import { CausaFactor } from './causas/causaFactor';
import { EfectoFactor } from './efectos/efectoFactor';
import { ControlFactor } from './controles/controlFactor';

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