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

export default function StockLocationRegisterDialog() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>
          <MdAdd/>
          Criar Local de Estoque
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cadastro de Local de Estoque</DialogTitle>
          <StockLocationRegisterForm />
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
