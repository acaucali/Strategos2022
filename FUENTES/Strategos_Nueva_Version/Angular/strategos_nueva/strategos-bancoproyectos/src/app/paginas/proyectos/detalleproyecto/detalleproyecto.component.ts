import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { IniciativaEstatusStrategos } from '../../configuracion/model-util/IniciativaEstatusStrategos';
import { MetodologiaStrategos } from '../../configuracion/model-util/MetodologiaStrategos';
import { OrganizacionStrategos } from '../../configuracion/model-util/organizacionstrategos';
import { TipoProyectoStrategos } from '../../configuracion/model-util/TipoProyectoStrategos';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { Proyectos } from '../../configuracion/model/proyectos';
import { TipoPoblacion } from '../../configuracion/model/tipopoblacion';
import { TiposObjetivos } from '../../configuracion/model/tiposobjetivos';
import { IniciativaEstatusStrategosService } from '../../configuracion/services-util/iniciativaestatusstrategos.service';
import { MetodologiaStrategosService } from '../../configuracion/services-util/metodologiastrategos.service';
import { OrganizacionStrategosService } from '../../configuracion/services-util/organizacionstrategos.service';
import { TipoProyectoStrategosService } from '../../configuracion/services-util/tipoproyectostrategos.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { ProyectoService } from '../../configuracion/services/proyectos.service';
import { TipoPoblacionService } from '../../configuracion/services/tipopoblacion.service';
import { TiposObejtivosService } from '../../configuracion/services/tiposobjetivos.service';

import Swal from 'sweetalert2';
import { PreproyectoComponent } from '../../preproyecto/preproyecto.component';
import swal from 'sweetalert2';
import { ProyectosComponent } from '../proyectos.component';
import { ModalService } from '../../gestionideas/detallegestion/modal.service';
import { ProyectosDocumentosAnexos } from '../../configuracion/model/proyectosdocumentosanexos';
import { ProyectosDocumentosService } from '../../configuracion/services/proyectosdocumentosanexos.service';
import { URL_BACKEND } from 'src/app/config/config';
import { DepartamentosService } from '../../configuracion/services/departamentos.service';
import { Departamentos } from '../../configuracion/model/departamentos';
import { ProyectosRegion } from '../../configuracion/model/proyectosregion';
import { SwitchComponent } from '@progress/kendo-angular-inputs';
import { ProyectosregionService } from '../../configuracion/services/proyectosregion.service';

interface Item {
  text: String;
  value: number;
}

@Component({
  selector: 'detalle-proyecto',
  templateUrl: './detalleproyecto.component.html',
  styleUrls: ['./detalleproyecto.component.css'],
})
export class DetalleproyectoComponent implements OnInit, AfterViewInit {
  private errores: string[];
  @Input() proyecto: Proyectos = new Proyectos();

  titulo: string = 'Datos del Proyecto';

  organizaciones: OrganizacionStrategos[];
  metodologias: MetodologiaStrategos[];
  alineaciones: TiposObjetivos[];
  estatus: IniciativaEstatusStrategos[];
  tipos: TipoProyectoStrategos[];
  preproyectos: Array<Proyectos>;
  poblaciones: TipoPoblacion[];
  poblacionesProyecto: TipoPoblacion[] = new Array<TipoPoblacion>();
  idea: IdeasProyectos = new IdeasProyectos();

  dropdownSettings: IDropdownSettings = {};
  dropdownSettingsOrganizacion: IDropdownSettings = {};
  dropdownSettingsIdea: IDropdownSettings = {};
  dropdownSettingsDepartamento: IDropdownSettings = {};

  departamentos: Departamentos[] = [];
  departamentoId: number;
  departamentoNombre: String;
  municipios: any[] = [];
  municipiosSeleccionados: any[] = [];
  proyectoRegion: ProyectosRegion[] = [];

  myForm: FormGroup;
  disabled = false;
  ShowFilter = false;
  limitSelection = false;
  nuevoDepartamento = false;

  selectedItems: Array<any> = [];
  disableBangalore: any;

  public source: Array<Item> = [];
  public sourceOrg: Array<{ text: String; value: number }> = [];

  public data: Array<Item>;
  public dataOrg: Array<{ text: String; value: number }>;
  public dataOrg2: Array<{ text: String; value: number }>;

  public selectedValue = 2;

  public documento: ProyectosDocumentosAnexos = new ProyectosDocumentosAnexos();
  private urlEndPoint: string =
    URL_BACKEND + '/api/strategos/bancoproyectos/documentoProyecto/';
  private archivoSeleccionado: File;

  list: any;

  constructor(
    public modalService: ModalService,
    private router: Router,
    private organizacionService: OrganizacionStrategosService,
    private ideaService: IdeasProyectosService,
    private tipoService: TipoProyectoStrategosService,
    private alineacionService: TiposObejtivosService,
    private estatusService: IniciativaEstatusStrategosService,
    private metodologiaService: MetodologiaStrategosService,
    private poblacionService: TipoPoblacionService,
    private fb: FormBuilder,
    private proyectoService: ProyectoService,
    private proyectoComponent: ProyectosComponent,
    private departamentosService: DepartamentosService,
    private proyectoRegionService: ProyectosregionService,
    public documentoService: ProyectosDocumentosService
  ) {}

  ngOnInit(): void {
    this.estatusService.getIniciativaEstatusList().subscribe((response) => {
      this.estatus = response;
    }); // obtiene los estatus
    this.organizacionService.getOrganizacionesList().subscribe((response) => {
      this.organizaciones = response;
    });
    this.alineacionService.getTiposObjetivoList().subscribe((response) => {
      this.alineaciones = response;
    });
    this.metodologiaService.getMetodologiasList().subscribe((response) => {
      this.metodologias = response;
    });
    this.tipoService.getTipoProyectosList().subscribe((response) => {
      this.tipos = response;
    });
    this.poblacionService.getPoblacionesList().subscribe((response) => {
      this.poblaciones = response;
    });

    this.departamentosService.getDepartamentosList().subscribe((response) => {
      this.departamentos = response;
    });

    if (this.proyectoComponent.isAdmin == true) {
      this.proyectoService
        .getProyectosListTipoId(true)
        .subscribe((response) => {
          this.preproyectos = response;
          if (this.preproyectos.length > 0) {
            this.preproyectos.forEach((pre) => {
              this.source.push({
                text: pre.nombreProyecto,
                value: pre.proyectoId,
              });
            });
          }
          this.data = this.source;
        });
    } else {
      this.proyectoService
        .getProyectosListOrgTipoId(this.proyectoComponent.organizacionId, true)
        .subscribe((response) => {
          this.preproyectos = response;
          if (this.preproyectos.length > 0) {
            this.preproyectos.forEach((pre) => {
              this.source.push({
                text: pre.nombreProyecto,
                value: pre.proyectoId,
              });
            });
          }
          this.data = this.source;
        });
    }

    this.organizacionService.getOrganizacionesList().subscribe((response) => {
      this.organizaciones = response;
      if (this.organizaciones.length > 0) {
        this.organizaciones.forEach((org) => {
          this.sourceOrg.push({ text: org.nombre, value: org.organizacionId });
        });
      }
      this.dataOrg = this.sourceOrg;
      this.dataOrg2 = this.sourceOrg;
    });

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'tipoPoblacionId',
      textField: 'poblacion',
      selectAllText: 'Seleccionar Todo',
      unSelectAllText: 'Quitar Todo',
      limitSelection: -1,
      clearSearchFilter: true,
      maxHeight: 197,
      itemsShowLimit: 3,
      allowSearchFilter: true,
      closeDropDownOnSelection: false,
      showSelectedItemsAtTop: false,
    };

    this.dropdownSettingsDepartamento = {
      singleSelection: false,
      idField: 'c_digo_dane_del_municipio',
      textField: 'municipio',
      selectAllText: 'Seleccionar Todo',
      unSelectAllText: 'Quitar Todo',
      limitSelection: -1,
      clearSearchFilter: true,
      maxHeight: 197,
      itemsShowLimit: 5,
      allowSearchFilter: true,
      closeDropDownOnSelection: false,
      showSelectedItemsAtTop: false,
    };

    this.myForm = this.fb.group({
      city: [this.selectedItems],
    });
  }

  ngAfterViewInit(): void {}

  cerrarModal() {
    this.modalService.cerrarModal();
  }

  onItemSelect(item: any) {
    var tipo: TipoPoblacion = new TipoPoblacion();
    tipo.tipoPoblacionId = item.tipoPoblacionId;
    tipo.poblacion = item.poblacion;
    this.poblacionesProyecto.push(tipo);
  }
  onSelectAll(items: any) {
    for (let item of items) {
      var tipo: TipoPoblacion = new TipoPoblacion();
      tipo.tipoPoblacionId = item.tipoPoblacionId;
      tipo.poblacion = item.poblacion;
      this.poblacionesProyecto.push(tipo);
    }
  }

  unSelect(item: any) {
    this.poblacionesProyecto = this.poblacionesProyecto.filter(
      (pobl) => pobl.tipoPoblacionId != item.tipoPoblacionId
    );
  }
  unSelectAll(items: any) {
    this.poblacionesProyecto = new Array<TipoPoblacion>();
  }

  public seleccionarIdea(proyecto: any): void {
    console.log('proyecto', proyecto.value);

    this.proyectoService
      .getProyecto(proyecto.value)
      .subscribe((pro) => (this.proyecto = pro));
    this.proyecto.estatusId = 7;
  }

  public seleccionarOrg(org: any): void {
    this.proyecto.dependenciaId = org.value;
  }

  public seleccionarOrgLid(org: any): void {
    this.proyecto.dependenciaLider = org.value;
  }

  handleLimitSelection() {
    if (this.limitSelection) {
      this.dropdownSettings = Object.assign({}, this.dropdownSettings, {
        limitSelection: 2,
      });
    } else {
      this.dropdownSettings = Object.assign({}, this.dropdownSettings, {
        limitSelection: null,
      });
    }
  }

  handleFilter(value) {
    this.data = this.source.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  handleFilterOrg(value) {
    this.dataOrg = this.sourceOrg.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  handleFilterOrg2(value) {
    this.dataOrg2 = this.sourceOrg.filter(
      (s) => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1
    );
  }

  create() {
    this.proyecto.poblaciones = this.poblacionesProyecto;
    console.log(this.proyecto);

    this.proyectoService.create(this.proyecto).subscribe(
      (json) => {
        swal.fire('Nuevo Proyecto', `${json.mensaje}`, 'success');
        this.cerrarModal();
        this.proyectoComponent.getProyectos();
        //this.ideaComponent.getIdeas();
      },
      (err) => {
        this.errores = err.error.errors as string[];
        console.error('Código error: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  update() {
    console.log(this.proyecto);
    this.proyectoService.update(this.proyecto, 2).subscribe(
      (json) => {
        swal.fire('Proyecto Actualizado', `${json.mensaje}`, 'success');
        this.cerrarModal();
        this.proyectoComponent.getProyectos();
      },
      (err) => {
        this.errores = err.error.errors as string[];
        console.error('Código error: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  agregar(forma: NgForm) {
    let municipio: String;
    let pro: ProyectosRegion;
    console.log(forma);
    for (let mun of forma.value.municipios) {
      let nombre;
      var values = forma.value.departamentoId.split('-');
      pro = {
        proyectoRegionId: undefined,
        departamentoId: values[0],
        municipioId: mun.c_digo_dane_del_municipio,
        proyectoId: undefined,
        departamentoNombre: values[1],
        municipioNombre: mun.municipio,
      };
      this.proyecto.departamentos.push(pro);
    }
    console.log(this.proyecto.departamentos);
    this.nuevoDepartamento = false;
  }

  onChange(value) {
    var values = value.split('-');
    this.departamentosService
      .getMunicipiosList(values[0])
      .subscribe((response) => {
        this.municipios = response;
      });
  }

  deleteMun(dep: ProyectosRegion): void {
    Swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar el municipio ${dep.municipioNombre} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.proyectoRegionService
          .delete(dep.proyectoRegionId)
          .subscribe((response) => {
            const jsLibraries = ['react', 'redux', 'vue', 'D3', 'Chart'];
            const filteredLibraries = jsLibraries.filter(
              (item) => item !== 'react'
            );
            swal.fire(
              'Municipio eliminado',
              'El Municipio ha sido eliminado con éxito',
              'success'
            );
          });
      }
    });
  }

  seleccionarFoto(event) {
    this.archivoSeleccionado = event.target.files[0];
    console.log(this.archivoSeleccionado);
  }

  subirDocumento() {
    this.documentoService
      .subirDocumento(
        this.proyecto.proyectoId,
        this.documento,
        this.archivoSeleccionado
      )
      .subscribe(
        (json) => {
          swal.fire('Documento subido', `${json.mensaje}`, 'success');
          this.documento = json.documento;
          this.proyecto.documentoId = this.documento.documentoId;
          console.log(this.proyecto);
          console.log(this.documento);
        },
        (err) => {
          this.errores = err.error.errors as string[];
          console.error('Código error: ' + err.status);
          console.error(err.error.errors);
        }
      );
  }

  eliminarDocumento(): void {
    swal
      .fire({
        title: 'Está seguro?',
        text: `¿Seguro que desea eliminar el documento?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, eliminar!',
        cancelButtonText: 'No, cancelar!',
      })
      .then((result) => {
        if (result.value) {
          this.documentoService
            .deleteDocumento(this.proyecto.documentoId)
            .subscribe((response) => {
              swal.fire(
                'Documento eliminado!',
                'El documento se ha eliminado con éxito',
                'success'
              );
            });
          this.proyecto.documentoId = 0;
        }
      });
  }

  descargarDocumento() {
    window.open(`${this.urlEndPoint}download/${this.proyecto.documentoId}`);
  }
}
