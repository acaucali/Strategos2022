import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ControlFactor } from 'src/app/paginas/configuracion/model/controlFactor';
import { Controles } from 'src/app/paginas/configuracion/model/controles';
import { EfectividadControles } from 'src/app/paginas/configuracion/model/efectividad';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { ControlFactorService } from 'src/app/paginas/configuracion/services/controles.factor.service';
import { ControlesService } from 'src/app/paginas/configuracion/services/controles.service';
import { EfectividadService } from 'src/app/paginas/configuracion/services/efectividad.service';
import swal from 'sweetalert2';
import { FactorriesgoComponent } from '../../factorriesgo.component';
import { ModalService } from './modal.service';
import { ControlesComponent } from '../controles.component';

@Component({
  selector: 'detalle-control-factor',
  templateUrl: './detalle-controles.component.html',
  styleUrls: ['./detalle-controles.component.css']
})
export class DetalleControlesComponent implements OnInit {

  private errores: string[];
  public controlRiesgo: Controles[];
  public efectividades: EfectividadControles[];
  public controlesString: Array<string> = [];
  public controlRi: Controles= new Controles();



  @Input() control: ControlFactor;

  @Input() factor: FactorRiesgo;

  public titulo: string = "Datos control factor";
  
  constructor( private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private controlRiesgoService: ControlesService,
    private controlFactorService: ControlFactorService,
    private efectividadService: EfectividadService, private controlFactorComponent: ControlesComponent, private factorComponent: FactorriesgoComponent
   ) { }

  ngOnInit() {
    let cont: number = 0;
    this.controlRiesgoService.getControlesListProceso(this.factorComponent.proceso.procesosId).subscribe(response =>{
      this.controlRiesgo = response;
      if(this.controlRiesgo.length >0){
      this.controlRiesgo.forEach(con =>{
        this.controlesString.push(con.control);
        cont ++;
      });
    }
    });
    this.efectividadService.getEfectividadList().subscribe(response => this.efectividades = response);
  }

  
  
  create(): void{
    this.controlRi.control = this.control.control;
    this.controlRi.controlDescripcion = this.control.descripcionControl;
    this.controlRi.procesoId = this.factorComponent.proceso.procesosId;

    this.controlFactorService.create(this.control, this.factor).subscribe(
      json => {
      swal.fire('Nuevo Control', `${json.mensaje}`, 'success');
      this.cerrarModal();  
      this.controlFactorComponent.controles= null;
      this.controlFactorComponent.getControles();
      this.controlRiesgoService.create(this.controlRi).subscribe(response => { console.log(response.mensaje) });  
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  
  

  update(): void{
    this.controlFactorService.update(this.control).subscribe(json =>{
      swal.fire('Control Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
      this.controlFactorComponent.controles= null;
      this.controlFactorComponent.getControles();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }
  

  public listItems: Array<string> = this.controlesString;

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

}
