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



@Injectable({
  providedIn: 'root'
})

export class PlanService {

  private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/plan';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  public planes: Plan[];
  public planProyecto: ProyectosPlan;

  constructor(private http: HttpClient, private router: Router) { }

  getPlanesList(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.planes = res as Plan[];
      return this.planes;
    }));
  }

  getProyectoPlanListId(id: number){
    return this.http.get(`${this.urlEndPoint}/proyecto/${id}`).pipe(map(res =>{
      this.planProyecto = res as ProyectosPlan;
      return this.planProyecto;
    }));
  }

  getPlanPage(page: number): Observable<any> {
    //return of(tarjetas);
    return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
      map((response: any) => {
        (response.content as Plan[]).map(tipos=>{
          return tipos;
        });
        return response;
      })
    );
  }

  create(plan: Plan) : Observable<any>{
    return this.http.post<any>(this.urlEndPoint, plan, {headers: this.httpHeaders}).pipe(
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

  getPlan(id): Observable<Plan>{
    return this.http.get<Plan>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/proyecto']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(plan: Plan): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${plan.planId}`, plan, {headers: this.httpHeaders }).pipe(
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

  delete(id: number): Observable<Plan>{
    return this.http.delete<Plan>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }


}
