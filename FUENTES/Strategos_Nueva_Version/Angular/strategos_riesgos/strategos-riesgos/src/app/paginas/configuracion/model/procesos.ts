import { Caracterizacion } from "./caracterizacion";
import { Producto } from "./producto";


export class Procesos {

    procesosId: number;
    procesoPadreId: number;
    procesoNombre: string;
    procesoCodigo: string;
    procesoTipo: number;
    responsable: string;
    descripcion: string;
    procesoDocumento: number;
    procesoProducto: Producto[];
    procesoCaracterizacion: Caracterizacion[];
    
}