import Link from 'next/link';
import EditIcon from '@icons/common/edit.svg';
import Button from '@/ui/atoms/Button';
import theme from '@/ui/theme';
import Label from '@/ui/atoms/Label';

function Edit() {
  return (
    <Link href="/profileForm?edit=true">
      <Label htmlFor="disability" text="장애 분류" />
      <Button
        variant="round"
        width="48px"
        color={theme.colors.assist.normal}
        aria-label="edit-button"
      >
        <EditIcon />
      </Button>
    </Link>
  );
}

export default Edit;
