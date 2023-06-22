import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { OrganizacionStrategos } from '../model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../model-util/TipoProyectoStrategos';




@Injectable({
  providedIn: 'root'
})

export class TipoProyectoStrategosService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/tipoproyecto';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public tipos: TipoProyectoStrategos[];

  constructor(private http: HttpClient, private router: Router) { }

  getTipoProyectosList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.tipos = res as TipoProyectoStrategos[];
      return this.tipos;
    }));
  }

  getTipoProyectos(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as TipoProyectoStrategos[]).map(tipo=>{
          return tipo;
        });
        return response;
      })
    );
  }

  create(tipo: TipoProyectoStrategos) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, tipo, {headers: this.httpHeaders}).pipe(
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

  getTipoProyecto(id): Observable<TipoProyectoStrategos>{
    return this.http.get<TipoProyectoStrategos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/gestionideas']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(tipo: TipoProyectoStrategos): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${tipo.tipoProyectoId}`, tipo, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<TipoProyectoStrategos>{
    return this.http.delete<TipoProyectoStrategos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
