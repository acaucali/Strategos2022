import { Injectable } from '@angular/core';

import { Observable, of , throwError} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

import { URL_BACKEND } from 'src/app/config/config';
import { TiposPropuestas } from '../model/tipospropuestas';
import { Plan } from '../model/plan';
import { ProyectosPlan } from '../model/proyectosplan';
import { Perspectiva } from '../model/perspectiva';
import { Arbol } from '../model-util/arbol';
import { Indicador } from '../model/indicador';
import { Iniciativa } from '../model/iniciativa';



@Injectable({
  providedIn: 'root'
})

export class IniciativaService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/iniciativa';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public iniciativas: Iniciativa[];
   

  constructor(private http: HttpClient, private router: Router) { }

  getIniciativasList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.iniciativas = res as Iniciativa[];
      return this.iniciativas;
    }));
  }

  getIniciativasPerspectiva(id: number){
    return this.http.get(`${this.urlEndPoint}/perspectiva/${id}`).pipe(map(res =>{
      this.iniciativas = res as Iniciativa[];
      return this.iniciativas;
    }));
  }

  getIniciativasPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Iniciativa[]).map(ind=>{
          return ind;
        });
        return response;
      })
    );
  }

  create(iniciativa: Iniciativa, perspectivaId: any) : Observable<any>{
    return this.http.post<any>(`${this.urlEndPoint}/${perspectivaId}`, iniciativa, {headers: this.httpHeaders}).pipe(
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

  getIniciativa(id): Observable<Iniciativa>{
    return this.http.get<Iniciativa>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(iniciativa: Iniciativa): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${iniciativa.iniciativaId}`, iniciativa, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Iniciativa>{
    return this.http.delete<Iniciativa>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
