import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EfectoFactor } from 'src/app/paginas/configuracion/model/efectoFactor';
import { Efectos } from 'src/app/paginas/configuracion/model/efectos';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { TipoImpacto } from 'src/app/paginas/configuracion/model/tipoimpacto';
import { EfectosService } from 'src/app/paginas/configuracion/services/efectos.service';
import { TipoimpactoService } from 'src/app/paginas/configuracion/services/tipoimpacto.service';
import swal from 'sweetalert2';
import { ModalService } from './modal.service';
import { efectoFactorService } from 'src/app/paginas/configuracion/services/efector.service';
import { EfectosComponent } from '../efectos.component';


@Component({
  selector: 'detalle-efecto-factor',
  templateUrl: './detalle-efecto.component.html',
  styleUrls: ['./detalle-efecto.component.css']
})
export class DetalleEfectoComponent implements OnInit {

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
    public modalservice: ModalService, 
    private efectoFactorService: efectoFactorService, private efectoService: EfectosService,
    private impactoService: TipoimpactoService, private efectoFactorComponent: EfectosComponent
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
