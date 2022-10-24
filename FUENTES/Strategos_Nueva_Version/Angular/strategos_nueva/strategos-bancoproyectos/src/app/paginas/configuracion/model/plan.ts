export class Plan {

    planId: number;
    organizacionId: number;
    planImpactaId: number;
    nombre: String;
    padreId: number;
    anoInicial: number;
    anoFinal: number;
    tipo: number;
    activo: number;
    revision: number;
    esquema: String;
    metodologiaId: number;
    claseId: number;
    claseIdIndicadoresTotales: number;
    indTotalImputacionId: number;
    indTotalIniciativaId: number;
    alerta: number;
    ultimaMedicionAnual: number;
    ultimaMedicionParcial: number;
    nlAnoIndicadorId: number;
    nlParIndicadorId: number;
    fechaUltimaMedicion: String;
}