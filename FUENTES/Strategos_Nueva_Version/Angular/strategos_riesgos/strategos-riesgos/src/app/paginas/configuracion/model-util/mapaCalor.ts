import { FactorRiesgo } from "../model/factor";

export class MapaCalor {
    color: string;
    probabilidad: number;
    impacto: number;
    severidad: number;
    factores: FactorRiesgo[];
    cantidadId: number;
}