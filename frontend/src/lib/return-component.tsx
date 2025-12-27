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
          className='w-16 bg-blue-500 text-white dark:bg-blue-950'
        >
          Alto
        </Badge>
      );
    case statusStatus.NORMAL:
      return (
        <Badge
          variant={'default'}
          className='w-16'
        >
          Normal
        </Badge>
      );
    case statusStatus.LOW:
      return (
        <Badge
          variant={'destructive'}
          className='w-16'
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
