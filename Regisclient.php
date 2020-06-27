<?php

defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

use Restserver\Libraries\REST_Controller;

class Regisclient extends REST_Controller
//ini controller buat client ya 
{

    function __construct($config = 'rest')
    {
        parent::__construct($config);
        $this->load->database();
    }

    function index_get()
    {
        $id = $this->get('id_');
        if ($id == '') {
            $regis = $this->db->get('client')->result();
        } else {
            $this->db->where('id', $id);
            $regis = $this->db->get('client')->result();
        }

        // echo json_encode($regis);
        $this->response($regis, 200);
    }

    function index_post()
    {

        $data = array(
            'id'           => $this->post('id'),
            'nama'          => $this->post('nama'),
            'telepon'    => $this->post('telepon'),
            'foto'    => 'avatar.jpg',
            'email'    => $this->post('email'),
            'password'    => md5($this->post('password')),
            'tipe_id'    => '3',
            'daftar'    => time(),


        );
        $insert = $this->db->insert('client', $data);
        if ($insert) {
            $this->response($data, 200);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

    function index_put()
    {
        $id = $this->put('id');
        $data = array(
            'id'           => $this->put('id'),
            'nama'          => $this->put('nama'),
            'telepon'    => $this->put('telepon'),
            'foto'    => $this->put('foto'),
            'email'    => $this->put('email'),
            'password'    => md5($this->put('password')),
            'tipe_id'    => '3',
            'daftar'    => time(),




        );
        $this->db->where('id', $id);
        $update = $this->db->update('client', $data);
        if ($update) {
            $this->response($data, 200);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

    function index_delete()
    {
        $id = $this->delete('id');
        $this->db->where('id', $id);
        $delete = $this->db->delete('notaris');
        if ($delete) {
            $this->response(array('status' => 'success'), 201);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }
}
// POST DIGUNAKAN UNTUK MENGIRIMKAN DATA
// PUT DIGUNAKAN UNTUK MENGUBAH/MENGEDIT DATA
// DELETA UNTUK MENGHAPUS DATA
