"use client"

import { EditStockLocation } from '@/components/EditStockLocation'
import { AlertDialogFooter, AlertDialogHeader } from '@/components/ui/alert-dialog'
import { Button } from '@/components/ui/button'
import { StockLocationData } from '@/types/types'
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogTitle, AlertDialogTrigger } from '@radix-ui/react-alert-dialog'
import { DropdownMenu, DropdownMenuContent, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@radix-ui/react-dropdown-menu'
import { ColumnDef } from "@tanstack/react-table"
import { ArrowUpDown, MoreHorizontal } from 'lucide-react'
import { MdDelete } from 'react-icons/md'
import { DataTable } from '../page'



const columns: ColumnDef<StockLocationData>[] = [
  {
    accessorKey: "id",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          ID
          <ArrowUpDown />
        </Button>
      )
    },
    cell: ({ row }) => {
      return <div className="capitalize">{row.getValue("id")}</div>
    }
  },
  {
    accessorKey: "name",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Nome
          <ArrowUpDown />
        </Button>
      )
    },
    cell: ({ row }) => <div className="lowercase">{row.getValue("name")}</div>,
  },
  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => {
      const location = row.original;
      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Abrir Menu</span>
              <MoreHorizontal />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Ações</DropdownMenuLabel>
            <EditStockLocation />
            <DropdownMenuSeparator />
            <AlertDialog>
              <AlertDialogTrigger asChild>
                <Button variant="outline" className='bg-red-500 hover:bg-red-800 cursor-pointer'><MdDelete className='text-white' /></Button>
              </AlertDialogTrigger>
              <AlertDialogContent>
                <AlertDialogHeader>
                  <AlertDialogTitle>Você tem certeza?</AlertDialogTitle>
                  <AlertDialogDescription>
                    Essa ação irá apagar os dados permanentemente.
                  </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancelar</AlertDialogCancel>
                  <AlertDialogAction>Continuar</AlertDialogAction>
                </AlertDialogFooter>
              </AlertDialogContent>
            </AlertDialog>
          </DropdownMenuContent>
        </DropdownMenu>
      )
    }
  }
];

interface Props {
  locations: StockLocationData[];
}

export default function LocationsDataTable({ locations }: Props) {
  return <DataTable columns={columns} data={locations} pageSize={20} />
}