import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import StockLocationRegisterForm from './stock-location-register-form';
import {MdAdd} from 'react-icons/md'
import { useState } from 'react';

export default function StockLocationRegisterDialog() {
  const [open, setOpen] = useState(false)
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button>
          <MdAdd/>
          Criar Local de Estoque
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cadastro de Local de Estoque</DialogTitle>
          <StockLocationRegisterForm onCloseDialog={() => setOpen(false)}/>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
