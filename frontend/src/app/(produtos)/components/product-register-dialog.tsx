import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import ProductRegisterForm from './product-register-form';

export default function ProductRegisterDialog() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>Criar Produto</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cadastro de Produto</DialogTitle>
          <ProductRegisterForm />
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
