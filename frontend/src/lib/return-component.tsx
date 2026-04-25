import { Badge } from '@/components/ui/badge';

enum statusStatus {
  HIGH = 'HIGH',
  NORMAL = 'NORMAL',
  LOW = 'LOW'
}

enum movementType {
  ENTRY = 'ENTRADA',
  EXIT = 'SAIDA'
}

export function returnBadgeComponent(status: string | undefined) {
  switch (status) {
    case statusStatus.HIGH:
      return (
        <Badge
          variant={'secondary'}
          className='w-16 text-amber-500 border border-amber-500'
        >
          Alto
        </Badge>
      );
    case statusStatus.NORMAL:
      return (
        <Badge
          variant={'secondary'}
          className='w-16 text-emerald-500 border border-emerald-500'
        >
          Normal
        </Badge>
      );
    case statusStatus.LOW:
      return (
        <Badge
          variant={'secondary'}
          className='w-16 text-destructive border border-destructive'
        >
          Baixo
        </Badge>
      );
    case movementType.ENTRY:
      return (
        <Badge
          variant={'default'}
          className='w-16'
        >
          Entrada
        </Badge>
      );
    case movementType.EXIT:
      return (
        <Badge
          variant={'secondary'}
          className='w-16'
        >
          Saída
        </Badge>
      );
  }
}
