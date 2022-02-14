import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';

import { HttpClientModule } from '@angular/common/http'; 
import { Router, ActivatedRoute } from '@angular/router';

import { FactorRiesgo } from '../../factor';
import { Causas } from 'src/app/tablas/causas/causas';
import { CausasService } from 'src/app/tablas/causas/causas.service';
import { ProbabilidadService } from 'src/app/tablas/probabilidad/probabilidad.service';
import { Probabilidad } from 'src/app/tablas/probabilidad/probabilidad';
import { EfectoFactor } from '../efectoFactor';
import { Controles } from 'src/app/tablas/controles/controles';
import { TipoimpactoService } from 'src/app/tablas/tipos-impacto/tipoimpacto.service';
import { EfectosFactorComponent } from '../efectosFactor.component';
import { TipoImpacto } from 'src/app/tablas/tipos-impacto/tipoimpacto';
import { efectoFactorService } from '../efector.service';
import { ModalEfectoFactorService } from './modalEfecto.service';
import { Efectos } from 'src/app/tablas/efectos/efectos';
import { EfectosService } from 'src/app/tablas/efectos/efectos.service';


@Component({
  selector: 'detalle-efecto-factor',
  templateUrl: './detalle.efecto.factor.html',
  styleUrls: ['./detalle.efecto.factor.css']
})
export class DetalleEfectoFactor implements OnInit {
  private errores: string[];
  public impactos: TipoImpacto[];
  public efectosRiesgo: Efectos[];
  public efectosString: Array<string> = [];
  public efectoRiesgo: Efectos = new Efectos();

  @Input() efecto: EfectoFactor;
  @Input() factor: FactorRiesgo;

  public titulo: string = "Datos efecto factor";
  
  constructor( private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalEfectoFactorService, 
    private efectoFactorService: efectoFactorService, private efectoService: EfectosService,
    private impactoService: TipoimpactoService, private efectoFactorComponent: EfectosFactorComponent
   ) { }

  ngOnInit() {
    let con: number = 0;
    this.efectoService.getEfectosList().subscribe(response =>{
      this.efectosRiesgo = response;
      if(this.efectosRiesgo.length >0){
      this.efectosRiesgo.forEach(efe =>{
        this.efectosString.push(efe.efecto);
        con ++;
      });
    }
    });
    this.impactoService.getImpactosList().subscribe(response => this.impactos = response);
  }

  
  
  create(): void{
    this.efectoRiesgo.efecto = this.efecto.efectoNombre;
    this.efectoRiesgo.efectoDescripcion = this.efecto.descripcionImpacto;

    this.efectoFactorService.create(this.efecto, this.factor).subscribe(
      json => {
      swal.fire('Nuevo Efecto', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.efectoFactorComponent.efectos= null;
      this.efectoFactorComponent.getEfectos(); 
      this.efectoService.create(this.efectoRiesgo).subscribe(response => { console.log(response.mensaje) });  

    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  

  update(): void{
    this.efectoFactorService.update(this.efecto).subscribe(json =>{
      swal.fire('Efecto Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
      this.efectoFactorComponent.efectos= null;
      this.efectoFactorComponent.getEfectos(); 
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  

  public listItems: Array<string> = this.efectosString;

  cerrarModal(){
    this.modalservice.cerrarModal();
  }
}
