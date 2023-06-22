import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Efectos } from 'src/app/paginas/configuracion/model/efectos';
import { EfectosService } from 'src/app/paginas/configuracion/services/efectos.service';
import swal from 'sweetalert2';
import { EfectosComponent } from '../efectos.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-efecto',
  templateUrl: './detalle-efectos.component.html',
  styleUrls: ['./detalle-efectos.component.css']
})
export class DetalleEfectosComponent implements OnInit {

  private errores: string[];

  @Input() efecto: Efectos;

  titulo: string = "Datos del efecto";
  constructor(private efectoService: EfectosService, private router: Router, 
    private activatedRoute: ActivatedRoute, private efectoComponent: EfectosComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.efectoService.create(this.efecto).subscribe(
      json => {
      swal.fire('Nuevo Efecto', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.efectoComponent.getEfectos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.efectoService.update(this.efecto).subscribe(json =>{
      swal.fire('Efecto Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

}
