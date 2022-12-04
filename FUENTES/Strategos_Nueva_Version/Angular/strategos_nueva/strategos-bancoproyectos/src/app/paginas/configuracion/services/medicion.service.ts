import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';

import { Medicion } from '../model/medicion';
import { DatoIdea } from '../model-util/DatoIdea';
import { DatoMedicion } from '../model-util/DatoMedicion';



@Injectable({
  providedIn: 'root'
})

export class MedicionService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/medicion';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public mediciones: Medicion[];
  public encabezados: DatoIdea[];
  public datos: DatoMedicion[];  
  public ids: number[];
   

  constructor(private http: HttpClient, private router: Router) { }

  getMedicionesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.mediciones = res as Medicion[];
      return this.mediciones;
    }));
  }

  getMedicionesIdsList(id: number, serie: number){
    return this.http.get(`${this.urlEndPoint}/ids/${id}/${serie}`).pipe(map(res =>{
      this.ids = res as number[];
      return this.ids;
    }));
  }

  getMedicionesIdsTareaList(id: number, serie: number){
    return this.http.get(`${this.urlEndPoint}/ids/tareas/${id}/${serie}`).pipe(map(res =>{
      this.ids = res as number[];
      return this.ids;
    }));
  }

  getMedicionesEncabezadosList(id: number, frecuencia: number, anio: number, perInicial: number, perFin: number){
    return this.http.get(`${this.urlEndPoint}/encabezados/${id}/${frecuencia}/${anio}/${perInicial}/${perFin}`).pipe(map(res =>{
      this.encabezados = res as DatoIdea[];
      return this.encabezados;
    }));
  }

  getMedicionesDatosList(id: number, anio: number, perInicial: number, perFin: number, serie: number){
    return this.http.get(`${this.urlEndPoint}/mediciones/${id}/${anio}/${perInicial}/${perFin}/${serie}`).pipe(map(res =>{
      this.datos = res as DatoMedicion[];
      return this.datos;
    }));
  }

  getMedicionesEncabezadosTareaList(id: number, frecuencia: number, anio: number, perInicial: number, perFin: number){
    return this.http.get(`${this.urlEndPoint}/encabezados/tarea/${id}/${frecuencia}/${anio}/${perInicial}/${perFin}`).pipe(map(res =>{
      this.encabezados = res as DatoIdea[];
      return this.encabezados;
    }));
  }

  getMedicionesEncabezadosPresupuestoList(){
    return this.http.get(`${this.urlEndPoint}/encabezados/presupuesto`).pipe(map(res =>{
      this.encabezados = res as DatoIdea[];
      return this.encabezados;
    }));
  }

  getMedicionesDatosPresupuestosList(id: number, anio: number, perInicial: number, perFin: number, serie: number){
    return this.http.get(`${this.urlEndPoint}/mediciones/presupuesto/${id}/${anio}/${perInicial}/${perFin}/${serie}`).pipe(map(res =>{
      this.datos = res as DatoMedicion[];
      return this.datos;
    }));
  }

  getMedicionesDatosTareaList(id: number, anio: number, perInicial: number, perFin: number, serie: number){
    return this.http.get(`${this.urlEndPoint}/mediciones/tarea/${id}/${anio}/${perInicial}/${perFin}/${serie}`).pipe(map(res =>{
      this.datos = res as DatoMedicion[];
      return this.datos;
    }));
  }
  
   create(datos: DatoMedicion[], objId: number) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${objId}`, datos,{headers: this.httpHeaders}).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje,e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  createTarea(datos: DatoMedicion[], actId: number) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/tarea/${actId}`, datos,{headers: this.httpHeaders}).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje,e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  createPresupuesto(datos: DatoMedicion[], anio: number) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/presupuesto/${anio}`, datos,{headers: this.httpHeaders}).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje,e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getMedicion(id): Observable<Medicion>{
    return this.http.get<Medicion>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(medicion: Medicion): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${medicion.medicionPk.indicadorId}`, medicion, {headers: this.httpHeaders }).pipe(
      catchError(e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Medicion>{
    return this.http.delete<Medicion>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
