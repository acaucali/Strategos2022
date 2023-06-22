import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CausaFactor } from 'src/app/paginas/configuracion/model/causaFactor';
import { Causas } from 'src/app/paginas/configuracion/model/causas';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { Probabilidad } from 'src/app/paginas/configuracion/model/probabilidad';
import { CausaFactorService } from 'src/app/paginas/configuracion/services/causa.factor.service';
import { CausasService } from 'src/app/paginas/configuracion/services/causas.service';
import { ProbabilidadService } from 'src/app/paginas/configuracion/services/probabilidad.service';
import swal from 'sweetalert2';
import { ModalService } from './modal.service';
import { CausasComponent } from '../causas.component';

@Component({
  selector: 'detalle-causa-factor',
  templateUrl: './detalle-causas.component.html',
  styleUrls: ['./detalle-causas.component.css']
})
export class DetalleCausasComponent implements OnInit {

  private errores: string[];
  public causasRiesgo: Causas[];
  public probabilidades: Probabilidad[];
  public causasString: Array<string> = [];

  @Input() causa: CausaFactor;

  @Input() factor: FactorRiesgo;

  public titulo: string = "Datos causa factor";
  
  constructor( private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private causaRiesgoService: CausasService,
    private causaFactorService: CausaFactorService,
    private probabilidadRiesgoService: ProbabilidadService, private causaFactorComponent: CausasComponent
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
