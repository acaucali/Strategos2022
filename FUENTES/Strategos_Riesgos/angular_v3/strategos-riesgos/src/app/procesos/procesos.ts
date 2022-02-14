import { Producto } from "./producto/producto";
import { Caracterizacion } from "./caracterizacion/caracterizacion";

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