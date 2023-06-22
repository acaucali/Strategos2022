import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { URL_BACKEND } from "src/app/config/config";
import { Usuario } from '../model/usuario';
import swal from 'sweetalert2';

@Injectable({
    providedIn: 'root'
  })
  
  export class UsuariosService {
  
    private urlEndPoint:string =URL_BACKEND+'/api/strategos/bancoproyectos/usuarios';
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    public usuarios: Usuario[];
  
    constructor(private http: HttpClient, private router: Router) { }
  
    getUsuariosList(){
      return this.http.get(this.urlEndPoint).pipe(map(res =>{
        this.usuarios = res as Usuario[];
        return this.usuarios;
      }));
    }
  
    getUsuarios(page: number): Observable<any> {
      //return of(tarjetas);
      return this.http.get(this.urlEndPoint+ '/page/'+page).pipe(
        map((response: any) => {
          (response.content as Usuario[]).map(usuario=>{
            return usuario;
          });
          return response;
        })
      );
    }
  
    create(usuario: Usuario) : Observable<any>{
      return this.http.post<any>(this.urlEndPoint, usuario, {headers: this.httpHeaders}).pipe(
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
  
    getUsuario(id): Observable<Usuario>{
      return this.http.get<Usuario>(`${this.urlEndPoint}/${id}`).pipe(
        catchError(e=>{
          this.router.navigate(['/banco/usuarios']);
          console.error(e.error.mensaje);
          swal.fire('Error al editar', e.error.mensaje, 'error');
          return throwError(e);
        })
      );
    }
  
    update(usuario: Usuario): Observable<any>{
      return this.http.put<any>(`${this.urlEndPoint}/${usuario.usuarioId}`, usuario, {headers: this.httpHeaders }).pipe(
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
  
    delete(id: number): Observable<Usuario>{
      return this.http.delete<Usuario>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeaders }).pipe(
        catchError(e =>{
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(e);
        })
      );
    }
  
  
  }
  