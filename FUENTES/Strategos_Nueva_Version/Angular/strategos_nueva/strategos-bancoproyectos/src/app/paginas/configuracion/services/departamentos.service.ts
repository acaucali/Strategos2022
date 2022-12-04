import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../model/estatusideas';
import swal from 'sweetalert2';
import { Departamentos } from '../model/departamentos';

@Injectable({
  providedIn: 'root',
})
export class DepartamentosService {
  private urlEndPoint: string =
    URL_BACKEND + '/api/strategos/bancoproyectos/departamento';
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  public departamentos: Departamentos[];
  public departamento: Departamentos;
  public municipios: any[] = [];
  public municipio: any;

  constructor(private http: HttpClient, private router: Router) {}

  getDepartamentosList() {
    return this.http.get(this.urlEndPoint).pipe(
      map((res) => {
        this.departamentos = res as Departamentos[];
        return this.departamentos;
      })
    );
  }

  getDepartamentoNombre(id: number) {
    return this.http.get(`${this.urlEndPoint}/${id}`).pipe(
      map((res) => {
        this.departamento = res as Departamentos;
        return this.departamento;
      })
    );
  }

  getMunicipiosList(id: number) {
    return this.http
      .get(
        `https://www.datos.gov.co/resource/xdk5-pm3f.json?c_digo_dane_del_departamento=${id}`
      )
      .pipe(
        map((res) => {
          this.municipios = res as any[];
          return this.municipios;
        })
      );
  }
}
