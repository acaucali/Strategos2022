import { FactorRiesgo } from '../factorriesgo/factor';

export class Ejercicios {

    ejercicioId: number;
    procesoPadreId: number;
    fechaEjercicio: Date;
    ejercicioDescripcion: string; 
    ejercicioEstatus: number;
    factorRiesgo: FactorRiesgo[];

}