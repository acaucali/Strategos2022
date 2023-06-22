import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { of } from 'rxjs';
import { Arbol } from '../../configuracion/model-util/arbol';
import { PerspectivaService } from '../../configuracion/services/perspectiva.service';
import { URL_BACKEND } from 'src/app/config/config';
import { PlanesComponent } from '../planes.component';
import { Plan } from '../../configuracion/model/plan';
import { Perspectiva } from '../../configuracion/model/perspectiva';
import { ModalService } from './detalle-perspectiva/modal.service';
import Swal from 'sweetalert2';
import { IndicadoresComponent } from '../indicadores/indicadores.component';
import { IniciativasComponent } from '../iniciativas/iniciativas.component';

@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.css'],
})
export class PlanComponent implements OnInit, AfterViewInit {
  @ViewChild('treeview') treeview: ElementRef;

  nodoSeleccionado: Arbol;
  public ver: boolean = false;
  public arbol: any[];
  public habilitar: boolean = false;
  nombrePerspectiva: any;

  public expandedKeys: any[] = [
    '0',
    '0_0',
    '0_1',
    '0_2',
    '0_3',
    '0_4',
    '0_5',
    '0_6',
    '0_7',
    '0_8',
    '0_9',
    '0_10',
    '0_11',
    '0_12',
    '0_13',
    '0_14',
    '0_15',
    '0_16',
    '0_17',
    '0_18',
    '0_19',
    '0_20',
    '0_21',
    '0_22',
    '0_23',
    '0_24',
    '0_25',
    '0_26',
    '0_27',
    '0_28',
    '0_29',
    '0_30',
    '0_31',
    '0_32',
    '0_33',
    '0_34',
    '0_35',
    '0_36',
    '0_37',
    '0_38',
    '0_39',
    '0_40',
    '0_41',
    '0_42',
    '0_43',
    '0_44',
    '0_45',
    '0_46',
    '0_47',
    '0_48',
    '0_49',
    '0_50',
  ];
  public selectedKeys: any[] = [0];

  public planId: any;

  perspectivaSeleccionada: Perspectiva;

  public hasChildren = (item: any) => item.items && item.items.length > 0;
  public fetchChildren = (item: any) => of(item.items);

  constructor(
    private perspectivaService: PerspectivaService,
    public modalservice: ModalService,
    public componentePadre: PlanesComponent
  ) {}

  ngOnInit(): void {
    if (localStorage.getItem('objetivoId') != null) {
      this.selectedKeys = [Number(localStorage.getItem('objetivoId'))];
    }

    this.perspectivaService
      .getPerspectivasArbol(localStorage.getItem('planId'))
      .subscribe((response) => (this.arbol = response));
  }

  ngAfterViewInit() {}

  crearPerspectiva(id: string) {
    if (id != '0') {
      this.perspectivaSeleccionada = new Perspectiva();
      this.perspectivaSeleccionada.padreId = Number(id);
      this.modalservice.abrirModal();
    }
    console.log(this.perspectivaSeleccionada);
  }

  editarPerspectiva(id: string) {
    if (id != '0') {
      this.perspectivaService
        .getPerspectiva(Number(id))
        .subscribe((response) => {
          this.perspectivaSeleccionada = response;
          this.modalservice.abrirModal();
        });
    }
  }

  eliminarPerspectiva(id: string) {
    Swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar la perspectiva?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.perspectivaService.delete(Number(id)).subscribe((response) => {
          this.getPerspectivas();
          Swal.fire(
            'Perspectiva eliminada!',
            'La perspectiva ha sido eliminada con éxito',
            'success'
          );
        });
      }
    });
  }

  onClick(id) {
    localStorage.setItem('objetivoId', id);

    this.perspectivaService
      .getPerspectiva(id)
      .subscribe((response) =>
        localStorage.setItem('nombreObjetivo', response.nombre.toString())
      );

    this.componentePadre.getIndicadoresIniciativas();
  }

  getPerspectivas() {
    this.arbol = [];
    this.perspectivaService
      .getPerspectivasArbol(localStorage.getItem('planId'))
      .subscribe((response) => (this.arbol = response));
  }

  decargarResumido() {
    window.open(
      URL_BACKEND +
        '/api/strategos/bancoproyectos/plan/pdf/' +
        localStorage.getItem('planId')
    );
  }

  decargarResumidoXLS() {
    window.open(
      URL_BACKEND +
        '/api/strategos/bancoproyectos/plan/excel/' +
        localStorage.getItem('planId')
    );
  }
}
