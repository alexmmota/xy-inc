import React, { Component } from 'react';
import './App.css';
import { Button, Row, Col, Input} from 'react-materialize';
import $ from 'jquery';
import Notifications, {notify} from 'react-notify-toast';

class App extends Component {

  constructor() {
    super();
    this.state = {
      id: '',
      name:'', 
      description: '', 
      category:'CLOTHES', 
      price: '0.00',
      listProducts: [],
      errors: []
    };
  }

  setName(event) {
    this.setState({name:event.target.value});
  }

  setDescription(event) {
    this.setState({description:event.target.value});
  }

  setCategory(event) {
    this.setState({category:event.target.value});
  }

  setPrice(event) {
    var aux = event.target.value.replace('.','');
    var auxN = parseInt(aux,10);
    aux = auxN.toString();
    if(aux.length === 0)
      aux = '000';
    if(aux.length === 1)
      aux = '00'.concat(aux);
    else if(aux.length === 2)
      aux = '0'.concat(aux);
    aux = aux.substr(0, aux.length-2).concat('.').concat(aux.substr(aux.length-2));

    this.setState({price:aux});
  }

  sendForm(event) {
    event.preventDefault();
  }


  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>Zup Dashboard</h2>
        </div>

        <Notifications />



        <Row>
          <Col s={6}>
            <div style={{textAlign: 'left'}}>
              <button type="button" className="btn-add btn">Novo</button>
            </div>
            <table className="striped">
              <thead>
                <tr>
                    <th data-field="id">Id</th>
                    <th data-field="name">Nome</th>
                    <th data-field="category">Categoria</th>
                    <th data-field="price">Preço</th>
                </tr>
              </thead>

              <tbody>
                {
                  this.state.listProducts.map(function (item, i) {
                    return <tr key={i} style={{cursor: 'pointer'}}><td>{item.id}</td><td>{item.name}</td><td>{item.category}</td><td>R$ {item.price}</td></tr>
                  }.bind(this))
                }
              </tbody>
            </table>          

          </Col>

          <Col s={6}>
            <h4>Produto</h4>

            <form role="form" onSubmit={this.sendForm.bind(this)} method="post">
              <Row>
                  <Input s={12} label="Id" placeholder="" value={this.state.id} disabled />
                  <Input s={12} label="Nome" placeholder="Nome" onChange={this.setName} value={this.state.name} />
                  <Input s={12} label="Descrição" placeholder="Descrição" onChange={this.setDescription} value={this.state.description} />
                  <Input s={12} type='select' label="Categoria" defaultValue='2' onChange={this.setCategory} value={this.state.category}>
                    <option value='CLOTHES'>Roupa</option>
                    <option value='ELECTRONIC'>Eletrônicos</option>
                    <option value='GAMES'>Jogos</option>
                    <option value='BOOKS'>Livros</option>
                  </Input>
                  <Input s={12} label="Preço" placeholder="Preço" onChange={this.setPrice} value={this.state.price} />
                  <Row>
                    <Col s={6}><Button type="submit" waves='light'>Salvar</Button></Col>
                    <Col s={6}><button type="button" className="btn-delete btn">Excluir</button></Col>
                  </Row>
              </Row>
            </form>
          
          </Col>
        </Row>

      </div>
    );
  }
}

export default App;
