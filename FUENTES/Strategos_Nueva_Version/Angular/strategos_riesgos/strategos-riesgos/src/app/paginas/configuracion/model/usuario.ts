export class Usuario {

    usuarioId: number;
    fullName: string;
    username: string;
    isAdmin: Boolean;
    timeStamp: Date;
    creado: Date;
    modificado: Date;
    instancia: string;
    estatus: number;
    habilitado: Boolean;
    pwd: string;
    pass: string;
    tipo: number; 
    roles: string[]=[];
}