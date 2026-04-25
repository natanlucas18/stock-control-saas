import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger
} from '@/components/ui/dialog';
import { PackagePlusIcon } from 'lucide-react';
import { useState } from 'react';
import ProductRegisterForm from './product-register-form';

export default function ProductRegisterDialog() {
  const [open, setOpen] = useState(false);
  return (
    <Dialog
      open={open}
      onOpenChange={setOpen}
    >
      <DialogTrigger asChild>
        <Button>
          <PackagePlusIcon /> Novo Produto
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cadastro de Produto</DialogTitle>
          <ProductRegisterForm onCloseDialog={() => setOpen(false)} />
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
