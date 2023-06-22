import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { Observable, of , throwError, from} from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import { Arbol } from '../model-util/arbolriesgo';
import { Caracterizacion } from '../model/caracterizacion';
import { Producto } from '../model/producto';
import { Procesos } from '../model/procesos';


@Injectable({
  providedIn: 'root'
})

export class ProcesosService {

  private urlEndPoint:string =URL_BACKEND+'/api/procesos/proceso';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  
  public arbol: Arbol[];
  public procedimientos: Caracterizacion[];
  public productos: Producto[];
  public procesos: Procesos[];
   
  constructor(private http: HttpClient, private router: Router) { }

  getProcesosArbol(){
    return this.http.get(this.urlEndPoint+ '/arbol/').pipe(map(res => {
      this.arbol = res as Arbol[];
        console.log(this.arbol);
        return this.arbol;
    }));
    
  }

  getProcesos(){
    return this.http.get(this.urlEndPoint).pipe(map(res =>{
      this.procesos = res as Procesos[];
      return this.procesos;
    }));
  }
  
  

  createProceso(proceso: Procesos) : Observable<any>{
    proceso.procesoDocumento=0;
    return this.http.post<any>(this.urlEndPoint, proceso, {headers: this.httpHeaders}).pipe(
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

  getProceso(id): Observable<Procesos>{
    return this.http.get<Procesos>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e=>{
        this.router.navigate(['/procesos']);
        console.error(e.error.mensaje);
        swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  getProcedimientos(id){
    return this.http.get(`${this.urlEndPoint}/procedimiento/${id}`).pipe(map(res => {
      this.procedimientos = res as Caracterizacion[];
        console.log(this.procedimientos);
        return this.procedimientos;
    }));
    
  }

  getProductos(id){
    return this.http.get(`${this.urlEndPoint}/producto/${id}`).pipe(map(res => {
      this.productos = res as Producto[];
        console.log(this.productos);
        return this.productos;
    }));
    
  }

  updateProceso(proceso: Procesos): Observable<any>{
    
    return this.http.put<any>(`${this.urlEndPoint}/${proceso.procesosId}`, proceso, {headers: this.httpHeaders }).pipe(
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
  
  deleteProceso(id: number): Observable<Procesos>{
    return this.http.delete<Procesos>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
