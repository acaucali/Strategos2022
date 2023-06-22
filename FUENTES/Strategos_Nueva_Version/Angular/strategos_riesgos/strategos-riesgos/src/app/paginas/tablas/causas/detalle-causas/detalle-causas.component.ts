import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Causas } from 'src/app/paginas/configuracion/model/causas';
import { CausasService } from 'src/app/paginas/configuracion/services/causas.service';
import swal from 'sweetalert2';
import { CausasComponent } from '../causas.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-causa',
  templateUrl: './detalle-causas.component.html',
  styleUrls: ['./detalle-causas.component.css']
})
export class DetalleCausasComponent implements OnInit {

  private errores: string[];

  @Input() causa: Causas;

  titulo: string = "Datos de la causa";
  constructor(private causasService: CausasService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private causaComponent: CausasComponent
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.causasService.create(this.causa).subscribe(
      json => {
      swal.fire('Nueva Causa', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.causaComponent.getCausas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.causasService.update(this.causa).subscribe(json =>{
      swal.fire('Causa Actualizada',  `${json.mensaje}`, 'success')
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
