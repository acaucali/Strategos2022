import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';

import { HttpClientModule } from '@angular/common/http'; 
import { Router, ActivatedRoute } from '@angular/router';
import { ModalCausaFactorService } from './modalCausa.service';
import { CausaFactor } from '../causaFactor';
import { FactorRiesgo } from '../../factor';
import { Causas } from 'src/app/tablas/causas/causas';
import { CausasService } from 'src/app/tablas/causas/causas.service';
import { ProbabilidadService } from 'src/app/tablas/probabilidad/probabilidad.service';
import { Probabilidad } from 'src/app/tablas/probabilidad/probabilidad';
import { CausasFactorComponent } from '../causasFactor.component';
import { CausaFactorService } from '../causa.factor.service';


@Component({
  selector: 'detalle-causa-factor',
  templateUrl: './detalle.causa.factor.html',
  styleUrls: ['./detalle.causa.factor.css']
})
export class DetalleCausaFactor implements OnInit {
  private errores: string[];
  public causasRiesgo: Causas[];
  public probabilidades: Probabilidad[];
  public causasString: Array<string> = [];

  @Input() causa: CausaFactor;

  @Input() factor: FactorRiesgo;

  public titulo: string = "Datos causa factor";
  
  constructor( private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalCausaFactorService, private causaRiesgoService: CausasService,
    private causaFactorService: CausaFactorService,
    private probabilidadRiesgoService: ProbabilidadService, private causaFactorComponent: CausasFactorComponent
   ) { }

  ngOnInit() {
    let con: number = 0;
    this.causaRiesgoService.getCausasList().subscribe(response =>{
      this.causasRiesgo = response;
      if(this.causasRiesgo.length >0){
      this.causasRiesgo.forEach(cau =>{
        this.causasString.push(cau.causaRiesgo);
        con ++;
      });
    }
    });
    this.probabilidadRiesgoService.getProbabilidadesList().subscribe(response => this.probabilidades = response);
  }

  
  
  create(): void{
    this.causaFactorService.create(this.causa, this.factor).subscribe(
      json => {
      swal.fire('Nueva Causa', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.causaFactorComponent.causas= null;
      this.causaFactorComponent.getCausas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  

  update(): void{
    this.causaFactorService.update(this.causa).subscribe(json =>{
      swal.fire('Causa Actualizada',  `${json.mensaje}`, 'success')
      this.cerrarModal();
      this.causaFactorComponent.causas= null;
      this.causaFactorComponent.getCausas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  

  public listItems: Array<string> = this.causasString;

  cerrarModal(){
    this.modalservice.cerrarModal();
  }
}
